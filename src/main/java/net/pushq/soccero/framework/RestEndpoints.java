package net.pushq.soccero.framework;

import net.pushq.soccero.endpoints.LemStatEndpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestEndpoints {

  List<AbstractEndpoint> endpoints = new ArrayList<>();

  public void register() {
    endpoints.add(new LemStatEndpoint());

    endpoints.forEach(AbstractEndpoint::register);
  }

  public List<String> locators() {
    return endpoints.
            stream().
            map(AbstractEndpoint::getLocator).
            collect(Collectors.toList());
  }
}
