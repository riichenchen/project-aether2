/*General add account stuffs :3*/
insert into accounts (username,password,loggedin) values (usr,pash,0);
insert into characters (name,level,mapid,accountId,characterType,x,y,z,hp,mp,attack,defense,money,exp,maxhp,maxmp,job) values ('x',1,'testMap',accId,4,400,1,300,100,250,30,10,200,0,100,250,0);

/*Skill Information to add to character*/
insert into skillset (accountId,skillId,skillLevel) values (accId,"icicle",0);
insert into skillset (accountId,skillId,skillLevel) values (accId,"blastburn",0);

/*Quest Information to add to account*/
/*insert into quests (*/