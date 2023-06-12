# Restaurant Management System App using Angular and Spring Boot
### I didn't use any frontend template or backend copy paste codes. I built this project from strach. It is my idea completely.

  #### Used Techonologies 
    - Bootstrap as frontend library (I also used it for designing of HTML of pdf. You can see it in pdf folder)
    - Angular Material as frontend library
    - MySQL as database
    - JWT for authentication
    - Thymeleaf for dynamic HTML in creating of pdf (You can see it in pdf folder)
    - PdfWriter library for create pdf 
    - AES encryption for encrypt password

  #### If you try run this project after download and it will not run. Because of :
    1. There is not database for project in your device.
    2. Database url isn't set in application.properties file.
    3. Username of database isn't set in application.properties file.
    4. Password of database isn't set in application.properties file.

  #### Solutions of problems :
    1. Create a new database for the project.
    2. Copy database url. 
    3. Go into rms/src/main/resources/application.properties file.
    4. Paste url in front of spring.datasource.url=. 
    4. Set username of database in front of spring.datasource.username=.
    5. Set password of database in front of spring.datasource.password=.

  #### How project works : 
    1. Insert at least one admin into admins_ table due to enter as admin. You can execute below query :
        - insert into admins_(username_,password_) values('adminadmin','mDBLQ1h5Uj830+CGIMxWFw==');
       Here 'mDBLQ1h5Uj830+CGIMxWFw==' is encrypted form of 'adminadmin'.
    2. Run backend of project.
    3. Run frontend of project.
    4. Go to http://localhost:4200.
    5. Enter username and password of admin. 
       - Password must be 8-280 character. 
       - Username must be 1-100 character.
       - Username musn't contain whitespace.
       - Both field is required. 
       - If you enter with wrong username or password and try to log in you will get warning message. 
       - If you enter with wrong username or password and try to log in inputs will be reset.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format log in button will be disabled. 
    6. Log in as admin by clicking button that have log in text on. After log in you will be redirected admin dashboard page. 
       Here you can see count of admins, users, categories, products, positions, employees and completed orders.
    7. Click button that have profile icon and edit profile text on on left side. Here you can change username and password of admin. 
       - Username length is 1-100 character.
       - Username can't contain whitespace. 
       - You can change password of admin by sliding slide button that have reset password text in front of.        
       - You can change username without changing password. 
       - Password and confirm password must be 8-280 character. 
       - Password and confirm password must be match.    
       - All fields is required.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format save button will be disabled.
    8. Go to users list by clicking button that have users text on on left side. Here you can perform add, edit and change status 
       operations. 
       - Add at least one user and be sure that status of that user is active.
       - User can't log in if status is not active. 
       - First name and last name musn't contain whitespace.
       - First name and last name must be 1-50 character. 
       - Username must be 1-100 character.
       - Username musn't contain whitespace. 
       - Username is unique. 
       - If you enter exists username and try to add new user you will get warning message.
       - Password and confirm password must be 8-280 character. 
       - Password and confirm password must match.  
       - All fields is required.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format add button will be disabled.
       - After adding new user you can edit and change status. 
       - You can change username without changing password. 
       - You can change password by sliding slide button that have reset password text in front of. 
    9. Go to positions list by clicking button that have positions text on on left side. Here you can perform add, edit and change 
       status operations. 
       - Add at least one position and be sure that status of that position is active.
       - In add new employee form position select box won't have any option if there isn't active position.
       - Title must be 1-100 character.
       - Title musn't contain only whitespace.
       - Title is unique. 
       - If you enter exists title and try to add new position you will get warning message.
       - Title is required.
       - If title field is in incorrect format you will get warning messages.
       - If title field is in incorrect format add button will be disabled.
       - After adding new positon you can edit and change status.
    10.Go employees list by clicking button that have employees text on on left side. Here you can perform add, edit and change status 
       operations. 
       - Add at least one employee.
       - First name and last name musn't contain whitespace.
       - First name and last name must be 1-50 character.  
       - Email must be 1-100 character. 
       - Email must be in email format.  
       - Email is unique. 
       - If you enter exists email and try to add new employee you will get warning message.
       - Phone must be 5-16 character with prefix.
       - Phone must be in phone number format.  
       - Phone is unique. 
       - If you enter exists phone and try to add new employee you will get warning message.
       - All fields is required.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format add button will be disabled.
    11.Go to categories list by clicking categories button on left side. Here you can add, edit and change status of category.
       - Add at least one position and be sure that status of that category is active.
       - In add new product form category select box won't have any option if there isn't active category.
       - Title must be 1-100 character. 
       - Title musn't contain only whitespace.
       - Title is unique. 
       - If you enter exists title and try to add new category you will get warning message.
       - Title is required.
       - If title field is in incorrect format you will get warning messages.
       - If title field is in incorrect format add button will be disabled.
       - After adding new category you can edit and change status.
    12.Go to products list by clicking button that have categories text on on left side. Here you can perform add, edit and change
       status operations.
       - Add at least one product and be sure that status of that product is active.
       - Users can't add product to order if there is not active product.
       - Title must be 1-100 character.
       - Title is unique. 
       - If you enter exists title and try to add new product you will get warning message.
       - Price of product range must be 0.01-9999.99. 
       - Price must be in spesific pattern. 
       - All fields is required.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format add button will be disabled.
       - After adding new product you can edit and change status.
    13.Log out by clicking button that have log out text on on left side. 
    14.Open window to enter login as user by clicking button that have log in as user text on on upper left corner. Here users 
       must enter own username and password.
       - Password must be 8-280 character. 
       - Username must be 1-100 character.
       - Username musn't contain whitespace.
       - Both field is required. 
       - If you enter with wrong username or password and try to log in you will get warning message. 
       - If you enter with wrong username or password and try to log in inputs will be reset.
       - If fields is in incorrect format you will get warning messages.
       - If fields is in incorrect format log in button will be disabled. 
    15.Enter by clicking button that have log in text on below. After entering you can see list of orders, print bill, perform edit, 
       add, complete, remove operations.
       - Add at least one order by clicking that have plus icon button.
       - Title must be 1-100 character.
       - Title musn't contain only whitespace.
       - Title in not unique.
       - Title is required.
       - If title field is in incorrect format you will get warning messages.
       - If title field is in incorrect format add button will be disabled.
       - After adding new order you can edit, remove, add product to order only.
    16.Open window by clicking button that have menu icon on. Here you can add product to order.
       - Choose one category.
       - Choose one product.
       - Add at least one product to order by clicking button that have plus icon on. 
       - Add product button will be disabled if product or category is not selected.
       - If order doesn't have any product you can't complete order. 
       - If order doesn't have any product you can't print bill.
       - After adding new product you can plus, minus or completely remove product from order.
       - Close window by clicking x button.
    17.Print bill by clicking button with bill icon on it.
    18.Complete order by clicking button with tick on it.
    19.Log out by clicking button with log out on it on upper left corner.
    20.Log in as admin again.
    21.Go to completed orders by clicking button with completed orders text on it. 
    22.Open window by clicking button with menu icon on it. Here you can see list of orderded products in that order.
    23.Close window by clicking x button. 
    24.Print bill by clicking button with printer icon on it.
    25.Log out again.    
    
 ##### Additional notes : 
    - You can also create tables and database using codes in RMS.sql.
    - I created pdf from html firstly. Then I used PdfWriter libaray. You can see files in pdf folder. 

    
