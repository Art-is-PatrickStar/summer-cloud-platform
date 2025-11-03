package com.wsw.cloud.product.domain.model.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 值对象：金额（以分为最小单位，避免精度问题）
 */
public final class Money {

    private final int amountInCents;

    private Money(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    public static Money ofCents(int amountInCents) {
        if (amountInCents < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return new Money(amountInCents);
    }

    public static Money ofYuan(BigDecimal yuan) {
        if (yuan == null) {
            return new Money(0);
        }
        if (yuan.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        BigDecimal cents = yuan.movePointRight(2).setScale(0, RoundingMode.HALF_UP);
        return new Money(cents.intValueExact());
    }

    public int getAmountInCents() {
        return amountInCents;
    }

    public BigDecimal toYuan() {
        return new BigDecimal(amountInCents).movePointLeft(2).setScale(2, RoundingMode.UNNECESSARY);
    }

    public boolean isPositive() {
        return amountInCents > 0;
    }

    public Money add(Money other) {
        return new Money(Math.addExact(this.amountInCents, other.amountInCents));
    }

    public Money subtract(Money other) {
        int result = this.amountInCents - other.amountInCents;
        if (result < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return new Money(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amountInCents == money.amountInCents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInCents);
    }

    @Override
    public String toString() {
        return toYuan().toPlainString();
    }

}


