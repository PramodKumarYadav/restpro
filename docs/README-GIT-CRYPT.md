# Setting up git-crypt to encrypt your secrets

Our secret keys are saved in `secrets.*` files and encrypted with [`git-crypt`](https://github.com/AGWA/git-crypt#readme).

> A user who is not added to the project, will not be able to use the secrets from our project and thus this is a mandatory
step to complete to be able to run the tests.

## Install Git-Crypt

### Step1: Verify if you have git crypt installed on your system

- macOS/Linux (run from terminal): `git-crypt --version`
- Windows (run from gitbash or powershell): `git-crypt --version`

### Step2: Install git crypt, if you haven`t already

> If you already see git-crypt installed in the previous step, skip.

Install `git-crypt` on your system:

- macOS (with homebrew) `brew install git-crypt`
- Windows - [Download git-crypt.exe and place it here: C:\Program Files\Git\cmd\git-crypt.exe](https://github.com/oholovko/git-crypt-windows).
- Linux `sudo apt install git-crypt`
- [Manual installation](https://github.com/AGWA/git-crypt/blob/master/INSTALL.md)

## Encrypt Project 

> One time activity, to be done by the very first user of this project.

> Note if the project is already git crypt-ed by another user, skip this section and go to the next section.

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
> Ignore git crypt key (in a real production world scenario). 
> I am not ignoring it here since its an open source project and anyone who wants to clone the project would need this 
> key to work with. 

> Ideally, if you were working in a company, this key would be preserved in a password manager such 
> as 1password from where everyone could download this key and decrypt files.

7. Push files to github
8. Check if files are encrypted on github by clicking on any secrets file in Github and by verifying that
text is not readable.

## Decrypt Project (in local)

> One time activity, to be done by every new user of this project.

Now once a user has initialized a project with git crypt

1. other new users can simply ask for the key from the first user
or download it from a central password manager tool (recommended) - such as `1password` or any other password manager tool.
2. They have to copy/paste this file in their cloned projects root directory.
3. Then run (only one time) below command to see the decrypted files.
   - `git-crypt unlock git-crypt-key-zero`

## Decrypt Project (in CI)

Refer information [here](https://github.com/sliteteam/github-action-git-crypt-unlock), to see how this was done. 

> One time activity, that is already done here: .github/workflows/run-tests-template.yml

FYI only - no further action needed by user. 

## Reference

- [git-crypt official github repo and readme file](https://github.com/AGWA/git-crypt)
- [A great article on this topic by Michael Bogan for Heroku](https://dev.to/heroku/how-to-manage-your-secrets-with-git-crypt-56ih)
