package com.andima.billing.core.useCases.transactions;

import com.andima.billing.core.request.Response;

import java.sql.SQLException;

/**
 * Created by GHIBOUB Khalid  on 18/08/2014.
 */
public interface Transaction {
    public Response execute() throws SQLException;
}
