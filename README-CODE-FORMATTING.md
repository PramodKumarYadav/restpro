# Code formatting Setup (one time)

[Back to readme.md](./README.md)

## To have common a code formatting for all users

### Step 1

- Install [pre-commit](https://pre-commit.com/) which is a package manager for installing pre-commit hooks.

### FYI Step: (fyi only - already done)

> NOTE: This particular step is already done and is only here for your information. No action is required from new users,
since Hook file is already copied in the project. I am adding this step here since it is good to know how this was done and so that if needed in future you can add (new) hooks here.

- Search for the pre commit hook you are interested in from the list of [available hooks](https://pre-commit.com/hooks.html).
- If you search with word "java" in the search window of above webpage, you will find (at least) 3 different pre commit hooks.
- All of these hooks use [Google Java Format program](https://github.com/google/google-java-format) that reformats Java source code to comply with [Google's Java codestyle](https://google.github.io/styleguide/javaguide.html).
- The hook we have chosen for our project is named `google-style-java` from user [matltz - on Github](https://github.com/maltzj/google-style-precommit-hook).
- Click on the above GitHub URL to go to see how to use this hook. You will see an example as below

  ``` repos:
        - repo: https://github.com/maltzj/google-style-precommit-hook
          sha: b7e9e7fcba4a5aea463e72fe9964c14877bd8130
          hooks:
            - id: google-style-java
  ```

  - Add a file in root of this repository named `.pre-commit-config.yaml` and paste above content in it (already done. Listed for fyi only).
  - Change tag `sha` to `rev` since that is how it used in latest versions (already done).
  - Add any other hooks you are interested in here (as you will see in file `.pre-commit-config.yaml`).

#### Step 2

- Each user need to run below step (Only one time ever for a project repo):
  - To install the git hook script on your local git repository:
    - run `pre-commit install` from the root of this repository.
    - Should give result as below if successful.

        ```bash
        $ pre-commit install
        pre-commit installed at .git/hooks/pre-commit
       ```

#### Step 3

- That's it you are now all set!
- Pre-commit will now run on every commit that you make and your code will be auto-magically formatted as per [Google's Java codestyle](https://google.github.io/styleguide/javaguide.html)
- If there are any formatting violations, which they will be almost "All-of-the-time" (as below). Not only it will tell you what the failures are
  but it will also fix it for you (of course you would need to add those changes to stage and commit)

    ```bash
    2023-02-25 20:40:19.553 [info] Google Java Code Style for Java......................(no files to check)Skipped
    Trim Trailing Whitespace.................................................Failed
    hook id: trailing-whitespace
    exit code: 1
    files were modified by this hook

    Fixing README.md
    ```bash
        - You can see failures here:
            - `Trim Trailing Whitespace.................................................Failed`
        - Here it tells you that the hook is fixing these issues for you:
            - ```
        files were modified by this hook

        Fixing README.md
        ```
        - Now all you need to do is stage and add those changes and that's it!
        - Once you stage and commit, you should see now a message as below where everything should pass.
        - ```
      2023-02-25 20:42:57.360 [info] Google Java Code Style for Java......................(no files to check)Skipped
      Trim Trailing Whitespace.................................................Passed
      Check Yaml...........................................(no files to check)Skipped
      Check JSON...........................................(no files to check)Skipped
      Pretty format JSON...................................(no files to check)Skipped
      Check Xml............................................(no files to check)Skipped
      Fix End of Files.........................................................Passed
      Don't commit to branch...................................................Passed
       ```

#### Step 4 [One time action for the whole project by any user]

When  pre-commit is first introduced in a project it is advised to format all the files at least
one time to avoid seeing formatting changes going forward. The way you can do this is by running
the below command in a powershell or a git terminal

> Note: It would not work from intellij or normal terminals.

`java -jar "C:\Users\Pramod Yadav\.cache\pre-commit\google-java-formatter1.16.0.jar" --replace $(git ls-files *.java)`

> Here you to replace the "Pramod Yadav" user with your user directory where this jar file was downloaded.
> Once formatted push all the changes in a PR and you can be assured that you dont have to see any formatting related
> issues anymore in your project PRs.
>
[Back to readme.md](./readme.md)
