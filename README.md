# How to encrypt and decrypt
* Install Tinkey cli to make keysets supereasily
* after that run the below codes to get private and corresponding public keysets
  ```
  tinkey create-keyset --key-template DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_AES_256_GCM --out-format JSON --out hybrid_test_private_keyset.json
  tinkey create-public-keyset --in hybrid_test_private_keyset.json --in-format JSON --out-format JSON --out hybrid_test_public_keyset.json
  ```
* build and run the main file and pass the  arguments as below
  ```
  java com.example.Main encrypt <your_public_keyset_file> <file_to_be_encrypted> <destination_of_encrypted_file>
  ```

  
