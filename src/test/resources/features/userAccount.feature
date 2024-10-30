@SelectTariffPlan
Feature: Select Tariff Plan
  As a eSim user
  I select the tariff plan
  So I should be able to see free trail screen

  @selectPlanViewTrialPeriod
  Scenario Outline: Should be able to select tariff plan
    Given I enter "Valid" email
    And I select magic link
    And I select "<type>" plan
    When I verify the trail period
    Then I logout of the account
    Examples:
      | type  |
      | small |
