CREATE TABLE admins
(
  admin_ID NUMBER NOT NULL,
  admin_password VARCHAR2(50) NOT NULL,
  PRIMARY KEY (admin_ID),
  UNIQUE (admin_password)
);


INSERT ALL
  INTO admins (admin_ID, admin_password) VALUES (190, 'password1')
  INTO admins (admin_ID, admin_password) VALUES (290, 'password2')
  INTO admins (admin_ID, admin_password) VALUES (390, 'password3')
  INTO admins (admin_ID, admin_password) VALUES (490, 'password4')
  INTO admins (admin_ID, admin_password) VALUES (590, 'password5')
  INTO admins (admin_ID, admin_password) VALUES (690, 'password6')
  INTO admins (admin_ID, admin_password) VALUES (790, 'password7')
  INTO admins (admin_ID, admin_password) VALUES (890, 'password8')
  INTO admins (admin_ID, admin_password) VALUES (990, 'password9')
  INTO admins (admin_ID, admin_password) VALUES (1090, 'password10')
SELECT * FROM dual;

insert into admins (admin_ID, admin_password) VALUES (1415, 'radwa');

