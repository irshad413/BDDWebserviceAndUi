Feature:
  Feature file created in JIRA through QTest Scenario

  Scenario: New feature file is updated to GitHub
    Given I am editing new story
    When I click on save
    Then feature file should be uploaded to GitHub
