Mini Ewallet Menggunakan Spring Boot

Step By Step :

1. Sudah harus terinstall Git, Maven dan PostgreSQL
2. Buka terminal menggunakan CMD atau Git Bash
3. Clone Repository pada link https://github.com/denny713/ewallet-java.git
4. Arahkan terminal pada folder project hasil clone repository (cd /{Project Directory})
5. Clean Project menggunakan command : mvn clean install
6. Run Project menggunakan Command : mvn spring-boot:run

Fitur (Harus login terlebih dahulu untuk proses data):
- Login : localhost:8070/login
- Logout : localhost:8070/logout
- Create User : localhost:8070/user/save
- Update User : localhost:8070/user/update
- Delete User : localhost:8070/user/delete
- Show User : localhost:8070/user (Show berdasarkan Status. Juka status Admin, maka akan tampil semua data, jika status selain Admin, maka akan tampil data sesuai user login)
- Show User Balance : localhost:8070/balance/user (Show berdasarkan Status. Juka status Admin, maka akan tampil semua data, jika status selain Admin, maka akan tampil data sesuai user login)
- Show Bank Balance : localhost:8070/balance/bank (Show berdasarkan Status. Juka status Admin, maka akan tampil semua data, jika status selain Admin, maka akan tampil data sesuai user login)
- Top Up Balance : localhost:8070/balance/topup (Hanya bisa topup sesuai user login)
- Transfer Balance : localhost:8070/balance/transfer (Hanya Bisa Transfer sesuai user login)

Bisa Ditest melalui Link Postman : https://www.getpostman.com/collections/d2fc40292d19c0931352

Juga bisa ditest melalui Swagger : localhost:8070/swagger-ui.html

Created By:

Denny Afrizal
