# UI Automation Project with Serenity + Screenplay + Cucumber

Welcome. This README is a **hands-on class** for a Manual QA who wants to learn automation from zero. It explains the concepts, the project structure, and how to run tests step by step.

---

## 1) What are you looking at?

This project automates the **SauceDemo** web app. We use:

- **Cucumber**: write tests in plain language (Gherkin).
- **Serenity BDD**: test framework + rich reports.
- **Screenplay**: modern automation pattern based on actors and tasks.

If you are new to automation, do not worry: this project is designed to teach you.

---

## 2) The main idea (simple explanation)

Think like this:

- An **Actor** is the user (example: "User").
- The Actor **does tasks** (login, open cart, add product).
- Then we ask **questions** to validate (is the product in the cart?).

That is Screenplay. It is designed for readability, reuse, and scalability.

---

## 3) Project structure (most important)

```
src/test/java/co/com/training_GI/
  hooks/          -> Prepare browser and actor
  tasks/          -> Actions the user performs
  questions/      -> Assertions and validations
  ui/             -> UI locators (Targets)
  stepdefinitions -> Connects Gherkin to Tasks/Questions

src/test/resources/features/
  login/          -> Login scenarios
  products/       -> Product scenarios
  cart/           -> Cart scenarios
  menu/           -> Hamburger menu scenarios
```

---

## 4) How does a test look?

Gherkin example:

```
Scenario: Add a product to the cart
  Given the user is logged in
  When the user adds the product "Sauce Labs Backpack" to the cart
  And the user opens the cart
  Then the cart should contain the product "Sauce Labs Backpack"
```

Each line maps to a Step Definition that calls a **Task** or a **Question**.

---

## 5) How to run tests (from zero)

### Run all tests:
```
.\gradlew.bat test
```

### Smoke (fast, critical):
```
.\gradlew.bat testSmoke
```

### Full regression:
```
.\gradlew.bat testRegression
```

### Only menu:
```
.\gradlew.bat testMenu
```

### Only products:
```
.\gradlew.bat testProducts
```

### Serenity report:
```
.\gradlew.bat test aggregate
```
Report path:
```
target/site/serenity/index.html
```

---

## 6) Browsers

Default browser is **Chrome**.

Choose a browser:
```
.\gradlew.bat test -Dwebdriver.driver=edge
```

Random browser (Chrome/Edge/Firefox):
```
.\gradlew.bat test -Drandom.browser=true
```

---

## 7) Single login (optional)

By default, each scenario logs in.  
If you want **one login for the whole run**:

```
.\gradlew.bat test -Dapp.session.reuse=true
```

This is useful locally to speed up.  
In CI, keep it `false` to avoid shared-state risks.

---

## 8) Credentials and environments

Credentials are in `src/test/resources/serenity.conf`:

```
app.username = "standard_user"
app.password = "secret_sauce"
```

Override without code changes:

```
.\gradlew.bat test -Dapp.username=standard_user -Dapp.password=secret_sauce
```

There are also environment profiles (dev/qa/stage):

```
.\gradlew.bat test -Denvironment=qa
```

---

## 9) How to add new cases

### A) Add a new product to data set
In `src/test/resources/features/products/products.feature`  
Add a new row:

```
| Sauce Labs Fleece Jacket |
```

### B) Create a new scenario
1. Write a new scenario in Gherkin.
2. If a step is missing, create it in the correct Step Definition class.
3. Implement the needed Task or Question.

---

## 10) Golden rules for beginners

- **Do not use sleeps**; use WaitUntil.
- **Create reusable Tasks** (think about reuse).
- **Keep locators in ui/**, never inside Steps.
- **Gherkin should be clear and readable** for business and QA.

---

## 11) How to study this project as Manual QA

Recommended learning path:

1. Read the features (Gherkin) and understand each scenario.
2. Open a Step Definition and see how it calls Tasks.
3. Open a Task and see what actions it performs.
4. Open a Question and see how we validate.

This flow teaches automation in a natural way.

---

## 12) Tutor tips

- If something fails, open the Serenity report.
- Think in **user actions** and **clear validations**.
- Automation is not "hard coding"; it is **clear business behavior**.

---

If you want, I can keep guiding you with exercises:
- Add a new test step by step
- Add stronger validations
- Build a basic CI pipeline

