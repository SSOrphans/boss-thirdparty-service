To get the proper secret files required for this service to work, visit the [visa developer center](https://developer.visa.com/) and sign in.
In the dashboard, select the Bank of Smoothstack project. On the left side of the menus, go into the credentials page and download the certificate pem file.

Once you have both the private key and the certificate keys, we now have to create a PKCS12 keystore file to be used by this service.

Inside the folder containing both pem files, run this command in the terminal.

`openssl pkcs12 -export -out visa-client-keystore.pkcs12 -in visa-client-cert.pem -inkey visa-key.pem `

Once the file has been generated, export the path of this newly generated keystore, and the password you gave it into the environment and name it `VISA_KEYSTORE_FILE` and `VISA_KEYSTORE_PASSWORD`.

Inside the same credentials page, copy the User ID and the password and export the values into the environment as `VISA_USER_ID` and `VISA_USER_PASSWORD` respectively.

---

In case you have lost your private key, in the credentials page you can generate a new key at the bottom of the page. Once you have downloaded your private key, your public key will be listed under that entry as Client Encryption Certificate.