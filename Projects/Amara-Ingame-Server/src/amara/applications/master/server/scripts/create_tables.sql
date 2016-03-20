CREATE TABLE IF NOT EXISTS CHARACTERS(ID SERIAL NOT NULL, NAME VARCHAR(1000) NOT NULL, TITLE VARCHAR(1000) NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS CHARACTERS_SKINS(ID SERIAL NOT NULL, CHARACTERID INTEGER NOT NULL, NAME VARCHAR(1000) NOT NULL, TITLE VARCHAR(1000) NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS ITEMS(ID SERIAL NOT NULL, NAME VARCHAR(1000) NOT NULL, TITLE VARCHAR(1000) NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS(ID SERIAL NOT NULL, SECURE_ID VARCHAR(1000) NOT NULL, LOGIN VARCHAR(1000) NOT NULL, PASSWORD VARCHAR(1000) NOT NULL, EMAIL VARCHAR(1000) NOT NULL, IS_ACTIVATED BOOLEAN, REGISTRATION_DATE BIGINT, LAST_MODIFICATION_DATE BIGINT, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS_CHARACTERS(ID SERIAL NOT NULL, USERID INTEGER NOT NULL, CHARACTERID INTEGER NOT NULL, SKINID INTEGER NOT NULL, INVENTORY INTEGER ARRAY[6] NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS_CHARACTERS_SKINS(ID SERIAL NOT NULL, USERID INTEGER NOT NULL, SKINID INTEGER NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS_ITEMS(ID SERIAL NOT NULL, USERID INTEGER NOT NULL, ITEMID INTEGER NOT NULL, AMOUNT INTEGER NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS_META(ID SERIAL NOT NULL, USERID INTEGER NOT NULL, KEY VARCHAR(1000) NOT NULL, VALUE VARCHAR(1000) NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS USERS_META_DEFAULTS(ID SERIAL NOT NULL, KEY VARCHAR(1000) NOT NULL, VALUE VARCHAR(1000) NOT NULL, PRIMARY KEY(ID));
CREATE TABLE IF NOT EXISTS WEBSITE_SESSIONS(ID VARCHAR(1000) NOT NULL, DATA VARCHAR(1000) NOT NULL, LAST_MODIFICATION_DATE BIGINT);