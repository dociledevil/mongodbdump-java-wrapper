# mongodbdump-java-wrapper
A simple java wrapper for mongodb backup commands : mongodump & mongorestore

## Goal
Goal is to provide to any java application, a simple and light way to backup a Mongo database:
- first locally through zip file on the local storage
- second remotely using custom plugin (example: box.com plugin to store backup zip file on the cloud : a remote secure box directory)

## Expected integration tests (not yet available)
- backup full database locally
  -  GIVEN: database name, backup name
  -  WHEN: BACKUP action
  -  THEN: fresh backup zip file created from the database
- restore locally
  -  GIVEN: database name, backup name
  -  WHEN: RESTORE action
  -  THEN: fresh database restore done from backup zip file

