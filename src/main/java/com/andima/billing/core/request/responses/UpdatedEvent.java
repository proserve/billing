package com.andima.billing.core.request.responses;

import com.andima.billing.core.request.Response;

public class UpdatedEvent implements Response {
  protected boolean entityFound = true;

  public boolean isEntityFound() {
    return entityFound;
  }
}
