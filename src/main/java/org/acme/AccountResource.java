package org.acme;

import javax.money.MonetaryAmount;

import org.acme.model.Account;
import org.javamoney.moneta.Money;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.vertx.core.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

public interface AccountResource extends PanacheEntityResource<Account, Long>{
    

    @Path("/tx")
    @POST
    // @Transactional
    public default Response transfer(JsonObject body) {
        System.out.println(body.encodePrettily());

        QuarkusTransaction.begin();

        Account from = Account.findById(body.getInteger("from"));
        Account to = Account.findById(body.getInteger("to"));
        MonetaryAmount amount = Money.of(body.getDouble("amount"), "EUR");

        Money f = Money.from(from.getBalance());
        from.setBalance(f.subtract(amount));
        
        Money t = Money.from(to.getBalance());
        to.setBalance(t.add(amount));

        from.persistAndFlush();
        to.persistAndFlush();

        QuarkusTransaction.commit();
        return Response.noContent().build();
    }
}
