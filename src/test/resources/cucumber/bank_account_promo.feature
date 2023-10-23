Feature: Bank account promo, get 10% extra in your $2000+ deposits, up to $500

  Scenario: Successfully promo applied, cap not reached.
    Given Account with a balance of 0
    When Trying to deposit 2000
    Then Account balance should be 2200

  Scenario: Successfully promo applied, cap reached.
    Given Account with a balance of 0
    When Trying to deposit 5000
    Then Account balance should be 5500

  Scenario: Promo not applied
    Given Account with a balance of 0
    When Trying to deposit 1500
    Then Account balance should be 1500
