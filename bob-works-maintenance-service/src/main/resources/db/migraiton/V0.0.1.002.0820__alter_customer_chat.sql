/**
  create table
 */
 alter table maintenance_customer_chat_rooms drop created_by;
 alter table maintenance_customer_chat_rooms drop last_modified_by;

alter table maintenance_customer_chats drop created_by;
alter table maintenance_customer_chats drop last_modified_by;