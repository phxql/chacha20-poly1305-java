# ChaCha20-Poly1305 in Java

Showcases the usage of the ChaCha20-Poly1305 cipher which [has been integrated into Java 11](https://openjdk.java.net/jeps/329).
 
Note: The nonce is generated via `SecureRandom`. As the nonce is only 96 bits long, this could, if encrypting a lot of messages with the same key,
lead to nonce reuse. A better approach is to use an increasing counter. **NEVER** reuse a nonce!

## License

[CC0](https://creativecommons.org/publicdomain/zero/1.0/deed.en)
