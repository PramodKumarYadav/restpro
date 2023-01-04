# Setting up git-crypt to encrypt your secrets

First time using git crypt - do this

## [One time activity] - for the whole project

> Note if the project is already git crypt-ed by another user, go to the next section.

Below are the steps that needs to be done only one time for the project by the very first user, who tries to
set up git -crypt in the project repository. Run below commands to git crypt the project.

> Install git crypt if not already installed.

1. `cd repo`
2. `git-crypt init`
3. `git-crypt export-key ./git-crypt-key-zero`
    - Save this in a central password manager - like `1password`.
4. define which files to encrypt in `.gitattributes` files.
    - Ex: `secrets.conf filter=git-crypt diff=git-crypt`
5. Check before committing.
   `git-crypt status`
6. Ignore the key `git-crypt-key-zero` from version control by adding it to the `.gitignore` file.
7. Push files to github
8. Check if files are encrypted on github by clicking on any secrets file in Github and by verifying that
text is not readable.

## [One time activity] - for each new user

Now once a user has initialized a project with git crypt

1. other new users can simply ask for the key from the first user
or download it from a central password manager tool (recommended) - such as `1password` or any other password manager tool.
2. They have to copy/paste this file in their cloned projects root directory.
3. Then run (only one time) below command to see the decrypted files.
   - `git-crypt unlock git-crypt-key-zero`

## [One time activity] - for decrypting secret files in CI

In the next video, we will see how we can decrypt our secret files in CI,so that our
secrets can be used in the project and our tests can run green again.

## Reference

- [git-crypt official github repo and readme file](https://github.com/AGWA/git-crypt)
- [A great article on this topic by Michael Bogan for Heroku](https://dev.to/heroku/how-to-manage-your-secrets-with-git-crypt-56ih)
