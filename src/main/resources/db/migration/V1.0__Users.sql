CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE users
(
 user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
 user_code VARCHAR(250) NOT NULL,
 password VARCHAR(250) NOT NULL
 );

INSERT INTO users VALUES (uuid_generate_v4(), 'ktest',
'$2a$10$zaJPLukPodM.arBNMVONWenOb7SFACvAPm2eq2shRNr/UltUnv0pa');