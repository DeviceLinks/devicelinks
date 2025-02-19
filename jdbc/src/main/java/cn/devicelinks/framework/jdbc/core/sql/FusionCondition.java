/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.jdbc.core.sql;

import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 融合数据过滤条件
 *
 * @author 恒宇少年
 * @see Condition
 * @see ConditionGroup
 * @see SortCondition
 * @see LimitCondition
 * @since 1.0
 */
@Getter
public class FusionCondition {
    private final List<Condition> conditions;
    private final List<ConditionGroup> conditionGroups;
    private final SortCondition sort;
    private final LimitCondition limit;

    private FusionCondition(List<Condition> conditions, List<ConditionGroup> conditionGroups, SortCondition sort, LimitCondition limit) {
        this.conditions = conditions;
        this.conditionGroups = conditionGroups;
        this.sort = sort;
        this.limit = limit;
    }

    public static FusionCondition empty() {
        return new FusionCondition(null, null, null, null);
    }

    public static FusionConditionBuilder withCondition(Condition condition) {
        Assert.notNull(condition, "Condition must not be null");
        return new FusionConditionBuilder().conditions(condition);
    }

    public static FusionConditionBuilder withConditions(Condition... conditions) {
        Assert.notEmpty(conditions, "Conditions must not be empty");
        return new FusionConditionBuilder().conditions(conditions);
    }

    public static FusionConditionBuilder withConditionGroup(ConditionGroup conditionGroup) {
        Assert.notNull(conditionGroup, "ConditionGroup must not be null");
        return new FusionConditionBuilder().conditionGroups(conditionGroup);
    }

    public static FusionConditionBuilder withConditionGroups(ConditionGroup... conditionGroups) {
        Assert.notEmpty(conditionGroups, "ConditionGroups must not be empty");
        return new FusionConditionBuilder().conditionGroups(conditionGroups);
    }

    public static FusionConditionBuilder withLimit(LimitCondition limit) {
        Assert.notNull(limit, "Limit must not be null");
        return new FusionConditionBuilder().limit(limit);
    }

    public static FusionConditionBuilder withSort(SortCondition sort) {
        Assert.notNull(sort, "Sort must not be null");
        return new FusionConditionBuilder().sort(sort);
    }

    public static class FusionConditionBuilder {
        private final List<Condition> conditions = new ArrayList<>();
        private final List<ConditionGroup> conditionGroups = new ArrayList<>();
        private SortCondition sort;
        private LimitCondition limit;

        public FusionConditionBuilder conditionGroups(ConditionGroup... conditionGroups) {
            if (!ObjectUtils.isEmpty(conditionGroups)) {
                this.conditionGroups.addAll(List.of(conditionGroups));
            }
            return this;
        }

        public FusionConditionBuilder conditions(Condition... conditions) {
            if (!ObjectUtils.isEmpty(conditions)) {
                this.conditions.addAll(List.of(conditions));
            }
            return this;
        }

        public FusionConditionBuilder sort(SortCondition sort) {
            this.sort = sort;
            return this;
        }

        public FusionConditionBuilder limit(LimitCondition limit) {
            this.limit = limit;
            return this;
        }

        public FusionCondition build() {
            return new FusionCondition(conditions, conditionGroups, sort, limit);
        }
    }
}
