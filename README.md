# JSecretStrings

A single tool to encryption and decryption of Strings through native Java JCA (Java Cryptography Architecture) classes
and optionally using custom security layers (Basic funny tricks) on top of Standard Cryptography Architecture.

### Use cases
Wherever that an application manipulates sensible/critical information, you can
easily securing the information by encrypt/decrypt messages from origin-to-receptor points.

### About the code
You can check: ```org.jsecretstrings.core``` package which is where all magic happens.<br/>
The ```org.jsecretstring.frontend.ConsoleClient``` class is a frontend to encrypt and decrypt strings on console.

### About a Graphic Client.
Only like a funny experiment, a Graphic Client(JavaFX) is available running from
class: ```org.jsecretstring.frontend.FXClient```.

### Contributors.

 * Giovanni Aguirre | [@DiganmeGiovanni](http://twitter.com/DiganmeGiovanni)
