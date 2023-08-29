package org.acme.model;

import javax.money.MonetaryAmount;

import org.hibernate.annotations.CompositeType;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class Account extends PanacheEntity {
    
    private String owner;
    private String name;
    private String accountType;
    private String usageType;

    private String number;

    @AttributeOverride(
        name = "amount",
        column = @Column(name = "balance_amount")
    )
    @AttributeOverride(
        name = "currency",
        column = @Column(name = "balance_currency")
    )
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount balance;

}
