package com.coc.data.controller.vo;

import com.coc.data.model.base.ClanWarLog;
import com.coc.data.model.base.ClanWarMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDataVO {

	private String season;

	private List<Map> detail;

	private List<MemberReport> reports;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberReport implements Comparable<MemberReport>{

		/**
		 * 排名信息
		 **/
		private String rankInfo;

		/**
		 * 玩家昵称
		 **/
		private String name;

		/**
		 * 玩家标签
		 **/
		private String tag;

		/**
		 * 黑三次数
		 **/
		private Integer attackNoStar;

		/**
		 * 一星次数
		 **/
		private Integer attackOneStar;

		/**
		 * 二星次数
		 **/
		private Integer attackTwoStar;

		/**
		 * 三星次数
		 **/
		private Integer attackThreeStar;

		/**
		 * 总进攻百分比
		 **/
		private Integer attackPercentage;

		/**
		 * 防守0星次数
		 **/
		private Integer defenseNoStar;

		/**
		 * 防守1星次数
		 **/
		private Integer defenseOneStar;

		/**
		 * 防守2星次数
		 **/
		private Integer defenseTwoStar;

		/**
		 * 被3次数
		 **/
		private Integer defenseThreeStar;

		/**
		 * 防守百分比
		 **/
		private Integer defensePercentage;

		/**
		 * 总进攻星星
		 **/
		private Integer attackStar;

		/**
		 * 总防守星星
		 **/
		private Integer defenseStar;

		/**
		 * 净得星星
		 **/
		private Integer netStar;

		public static MemberReport compute(List<ClanWarLog> warLogList, ClanWarMember clanWarMember) {
			MemberReport report = mockReport();
			report.setName(clanWarMember.getMemberName());
			report.setTag(clanWarMember.getMemberTag());
			for (ClanWarLog clanWarLog : warLogList) {
				if (clanWarLog.getAttackerTag().equals(clanWarMember.getMemberTag())) {
					switch (clanWarLog.getStar()) {
						case 0:
							report.setAttackNoStar(report.getAttackNoStar() + 1);
							break;
						case 1:
							report.setAttackOneStar(report.getAttackOneStar() + 1);
							break;
						case 2:
							report.setAttackTwoStar(report.getAttackTwoStar() + 1);
							break;
						case 3:
							report.setAttackThreeStar(report.getAttackThreeStar() + 1);
							break;
						default:
							break;
					}
					report.setAttackStar(report.getAttackStar() + clanWarLog.getStar());
					report.setAttackPercentage(report.getAttackPercentage() + Integer.parseInt(clanWarLog.getDestructionPercentage()));
				} else if (clanWarLog.getDefenderTag().equals(clanWarMember.getMemberTag())) {
					switch (clanWarLog.getStar()) {
						case 0:
							report.setDefenseNoStar(report.getDefenseOneStar() + 1);
							break;
						case 1:
							report.setDefenseOneStar(report.getDefenseOneStar() + 1);
							break;
						case 2:
							report.setDefenseTwoStar(report.getDefenseTwoStar() + 1);
							break;
						case 3:
							report.setDefenseThreeStar(report.getDefenseThreeStar() + 1);
							break;
						default:
							break;
					}
					report.setDefenseStar(report.getDefenseStar() + clanWarLog.getStar());
					report.setDefensePercentage(report.getDefensePercentage() + Integer.parseInt(clanWarLog.getDestructionPercentage()));
				}
			}
			report.calculate();

			return report;
		}

		public void calculate() {
			this.netStar = this.attackStar - this.defenseStar;
		}

		public static MemberReport mockReport() {
			MemberReport report = new MemberReport();
			report.setAttackNoStar(0);
			report.setAttackOneStar(0);
			report.setAttackTwoStar(0);
			report.setAttackThreeStar(0);
			report.setAttackStar(0);
			report.setAttackPercentage(0);
			report.setDefenseNoStar(0);
			report.setDefenseOneStar(0);
			report.setDefenseTwoStar(0);
			report.setDefenseThreeStar(0);
			report.setDefenseStar(0);
			report.setDefensePercentage(0);
			report.setNetStar(0);

			return report;
		}

		@Override
		public int compareTo(MemberReport o) {
			// 先比较净星
			if (this.netStar.equals(o.netStar)) {
				// 再比较三星次数
				if (this.attackThreeStar.equals(o.attackThreeStar)) {
					// 再比较进攻百分比
					if (this.attackPercentage.equals(o.attackPercentage)) {
						// 再比较防守百分比
						if (this.defensePercentage.equals(o.defensePercentage)) {
							return 0;
						} else {
							return o.attackPercentage.compareTo(this.attackPercentage);
						}
					} else {
						return this.attackPercentage.compareTo(o.attackPercentage);
					}
				} else {
					return this.attackThreeStar.compareTo(o.attackThreeStar);
				}
			}

			return this.netStar.compareTo(o.netStar);
		}
	}
}
