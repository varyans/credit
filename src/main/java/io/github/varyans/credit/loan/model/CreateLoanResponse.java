package io.github.varyans.credit.loan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanResponse {
    private  Long id;
    private  boolean paid;
    private  BigDecimal amount;
    private  LocalDate createDate;
    private  List<LoanInstallmentEntity> installments;


    public Long id() {
        return id;
    }

    public boolean paid() {
        return paid;
    }

    public BigDecimal amount() {
        return amount;
    }

    public LocalDate createDate() {
        return createDate;
    }

    public List<LoanInstallmentEntity> installments() {
        return installments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreateLoanResponse) obj;
        return Objects.equals(this.id, that.id) &&
                this.paid == that.paid &&
                Objects.equals(this.amount, that.amount) &&
                Objects.equals(this.createDate, that.createDate) &&
                Objects.equals(this.installments, that.installments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paid, amount, createDate, installments);
    }

    @Override
    public String toString() {
        return "CreateLoanResponse[" +
                "id=" + id + ", " +
                "paid=" + paid + ", " +
                "amount=" + amount + ", " +
                "createDate=" + createDate + ", " +
                "installments=" + installments + ']';
    }

}
