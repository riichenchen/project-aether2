set @username = 'bobert';
set @password = '01d5f7dbc48bef81a4e86c820c547df135dca6fa';
set @characterName = 'sir_bobert';

/*General add account stuffs :3*/
insert into accounts(username,password,loggedin) values (@username,@password,0);
set @accId = SELECT AUTO_INCREMENT FROM accounts;

insert into characters (name,level,mapid,accountId,characterType,x,y,z,hp,mp,attack,defense,money,exp,maxhp,maxmp,job) values (@characterName,1,'testMap',@accId,4,400,1,300,100,250,30,10,200,0,100,250,0);

/*Skill Information to add to character*/
insert into skillset (accountId,skillId,skillLevel) values (@accId,"icicle",0);
insert into skillset (accountId,skillId,skillLevel) values (@accId,"blastburn",0);

/*Quest Information to add to character*/
insert into quests(accountId,questId,status) values (@accId,'firstA',0);
/*Quest Requirements to add to character*/
insert into questdata(accountId,questId,requiredMob,number,requiredNumber)
  values (@accId,'firstA','billyscow',0,10);

-- todo: moar quests
-- insert into quests(accountId,questId,status) values (@accId,'questName',0);