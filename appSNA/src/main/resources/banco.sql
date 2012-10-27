
CREATE  TABLE Usuario (
  id_usuario BIGINT NOT NULL ,
  nome VARCHAR(45) NOT NULL ,
  screen_name VARCHAR(20) NOT NULL ,
  biografia TEXT NULL ,
  localizacao VARCHAR(30) NULL ,
  total_following INT NULL ,
  total_followers INT NULL ,
  total_tweets INT NULL ,
  URL TEXT NULL ,
  timezone VARCHAR(20) NULL ,
  linguagem VARCHAR(20) NULL ,
  data_criacao DATE NULL ,
  url_imagem TEXT NULL ,
  PRIMARY KEY (id_usuario) ,
  UNIQUE INDEX screen_name_UNIQUE (screen_name ASC) 
  );


CREATE TABLE Relacionamento (
  id_souce BIGINT NOT NULL ,
  id_target BIGINT NOT NULL ,
  PRIMARY KEY (id_souce, id_target),
  CONSTRAINT fk_Relacionamento_Usuario1
    FOREIGN KEY (id_souce)
    REFERENCES Usuario (id_usuario),     
  CONSTRAINT fk_Relacionamento_Usuario2
    FOREIGN KEY (id_target)
    REFERENCES Usuario (id_usuario)
   );
   
   
  CREATE  TABLE Status (
  id_status INT NOT NULL AUTO_INCREMENT ,
  id_usuario BIGINT NOT NULL ,
  data_criacao DATE NULL ,
  texto VARCHAR(150) NOT NULL ,
  latitude VARCHAR(20) NULL ,
  longitude VARCHAR(20) NULL ,
  total_retweet INT NULL ,
  is_retweeted INT NULL ,
  PRIMARY KEY (id_status, id_usuario) ,  
  CONSTRAINT fk_status_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES Usuario (id_usuario)
    );
   
   
  CREATE  TABLE Status (
  id_status INT NOT NULL AUTO_INCREMENT ,
  id_usuario BIGINT NOT NULL ,
  data_criacao DATE NULL ,
  texto VARCHAR(150) NOT NULL ,
  latitude VARCHAR(20) NULL ,
  longitude VARCHAR(20) NULL ,
  total_retweet INT NULL ,
  is_retweeted INT NULL ,
  PRIMARY KEY (id_status, id_usuario) ,  
  CONSTRAINT fk_status_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES Usuario (id_usuario)
    );
	

  CREATE  TABLE URLMention (
  id_usuario BIGINT NOT NULL ,
  id_status INT NOT NULL ,
  id_urlmention INT NOT NULL AUTO_INCREMENT ,
  url TEXT NULL ,
  PRIMARY KEY (id_usuario, id_status, id_urlmention),  
  CONSTRAINT fk_URLMention_Status1
    FOREIGN KEY (id_status , id_usuario)
    REFERENCES Status (id_status , id_usuario)
  );
  
 
CREATE  TABLE UserMention (
  id_usuario BIGINT NOT NULL ,
  id_status INT NOT NULL ,
  id_usermention INT NOT NULL AUTO_INCREMENT ,
  usuario VARCHAR(45) NULL ,
  PRIMARY KEY (id_usuario, id_status, id_usermention),
  CONSTRAINT fk_UserMention_Status1
    FOREIGN KEY (id_status , id_usuario)
    REFERENCES Status (id_status , id_usuario)
    );
