# mongodbdump-java-wrapper
A simple java wrapper for mongodb backup commands : mongodump & mongorestore

## Goal
Goal is to provide to any java application, a simple and light way to backup a Mongo database:
- first locally through zip file on the local storage
- (planned) second remotely using custom plugin (example: dropbox.com plugin to store backup zip file on the cloud : a remote secure dropbox directory)

## HowTo backup/restore database to/from DropBox 

- subscribe and login onto dropbox.com
- create a [new dropbox application](https://www.dropbox.com/developers/apps/create) for your backup (example : MongoWrapper)
- configure your environment to use dropbox service: 
-- (mandatory) dropbox [access token of your app](https://www.dropbox.com/developers/apps/info/) 
-- an application name

example :
DROPBOX_TOKEN="EMYWONDERFULLTOKEN_HERE_FROM_DROPBOX_APP_INFOH"
DROPBOX_APPLICATION="MyWrapper"

## Integration tests 
Integration tests are good way to know how to use this library. Each main feature is described with a dedicated integration test.

###MongodumpServiceITest
- backup full database locally
  -  GIVEN: database name
  -  WHEN: BACKUP action
  -  THEN: fresh backup zip file created from the database
- restore locally
  -  GIVEN: database name, backup file name
  -  WHEN: RESTORE action
  -  THEN: fresh database restore done from backup zip file

###DropboxMongoBackupServiceITest
- backup full database on dropbox
  -  GIVEN: database name
  -  WHEN: BACKUP action
  -  THEN: fresh backup zip file uploaded onto dropbox (created from the database)
- restore dropbox backup
  -  GIVEN: database name, dropbox backup file name
  -  WHEN: RESTORE action
  -  THEN: fresh database restore done from downloaded backup zip file

### Contributions
Contributions are welcome through feature branch and pull request. 

If you encounter issue, please provide details on a new [ticket](https://github.com/boly38/mongodbdump-java-wrapper/issues).

### TODO list
 - add cloud save/restore feature (ex. a [dropbox.com](https://www.dropbox.com/) directory)
 - add the way to auto-detect mongo executables on an openshift context (at runtime on an openshift gear)