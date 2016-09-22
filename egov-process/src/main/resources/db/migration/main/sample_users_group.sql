INSERT INTO eg_group VALUES (1, 'Superusers', 'security-role');
INSERT INTO eg_group VALUES (2, 'financials', 'assignment');
INSERT INTO eg_group VALUES (3, 'Assets', 'assignment');



INSERT INTO eg_user(id,firstname,lastname,email,username,password) VALUES (1,  'admin', 'Administrator', 'admin', 'test', NULL);
INSERT INTO eg_user(id,firstname,lastname,email,username,password) VALUES (2, 'mani', 'kanta', 'manikanta@egovernments.org','mani', 'egov');
INSERT INTO eg_user(id,firstname,lastname,email,username,password) VALUES (3, 'kavya', 'kavya', 'kavya.ys@egovernments.org', 'kavya', 'egov');
INSERT INTO eg_user(id,firstname,lastname,email,username,password) VALUES (4, 'venki', 'venki', 'venki@egovernments.org','venki', 'egov' );
INSERT INTO eg_user(id,firstname,lastname,email,username,password) VALUES (5, 'elzan', 'mathew', 'elzan.mathew@egovernments.org','elzan', 'egov' );


INSERT INTO  eg_usergroup  (user_id,group_id) VALUES (1, 1);
INSERT INTO  eg_usergroup (user_id,group_id)  VALUES (3, 2);
INSERT INTO  eg_usergroup (user_id,group_id)  VALUES (4, 2);
INSERT INTO  eg_usergroup (user_id,group_id)  VALUES (2, 2);
INSERT INTO  eg_usergroup  (user_id,group_id)  VALUES (5, 2);
INSERT INTO  eg_usergroup (user_id,group_id)  VALUES (2, 3);
INSERT INTO  eg_usergroup (user_id,group_id)  VALUES (5, 3);
