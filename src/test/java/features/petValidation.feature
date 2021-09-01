@PetAPI
Feature: Validating pet API

  Scenario Outline: Verify I can get pet details by id
  #  Given verify pet details by getPetAPI
    Given Add pet Payload with "<id>" "<petName>" "<category>" "<photoURL>" "<tagName>" "<status>"
    When The user calls "AddPetAPI" with "POST" http request
    Then API call got success with status code 200
    And verify pet_Id created maps to "<petName>" "<status>" using "getPetAPI"

    Examples:
    |id  | petName   | category   | photoURL 	        |tagName 	 |status|
    |1 |  Rabbit |Bunny category| www.Bunny.com | Bunny123 | low |


  Scenario Outline: Update newly created pet details

    Given Update the pet with "<petName>" and "<status>"
    When The user calls "UpdateAPI" with "POST" http request
    Then API call got success with status code 200
    And verify pet_Id created maps to "<petName>" "<status>" using "getPetAPI"

    Examples:
      |petName  | status   |
      | NewName |  NewStatus |

  Scenario: Delete pet payload and confirm it is deleted

    Given DeletePet Payload
    When The user calls "deletePetAPI" with "DELETE" http request
    Then The user calls "deletePetAPI" with "DELETE" http request
    Then API call got success with status code 200
    And  Get pet payload
    Then API call got success with status code 404