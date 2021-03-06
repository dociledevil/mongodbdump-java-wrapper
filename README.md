# mongodbdump-java-wrapper
A simple java wrapper for mongodb backup commands : mongodump & mongorestore

## Goal
Goal is to provide to any java application, a simple and light way to backup a Mongo database:
- backup/restore locally through zip file on the local storage
- backup/restore remotely using dropbox.com API - store backup zip file on the cloud : a remote secure dropbox directory

## GowTo setup maven dependency
Add in your pom

			<dependency>
				<groupId>com.github.boly38</groupId>
				<artifactId>mongodbdump-java-wrapper</artifactId>
				<version>1.4</version>
			</dependency>

## HowTo use from command line
Under bash-like console, you could execute `_wrapper.sh`.
Otherwise, launch the following command:

    java -Dlog4j.configuration=file:log4j.properties -jar target/mongodbdump-java-wrapper-1.4.jar -h

## HowTo use from java code : backup/restore database to/from DropBox 

- subscribe and login onto [dropbox.com](https://www.dropbox.com/)
- create your [dropbox application](https://www.dropbox.com/developers/apps/create) for your backup (example : MongoWrapper)
- configure your environment to use dropbox service: (mandatory) a dropbox [access TOKEN of your app](https://www.dropbox.com/developers/apps/info/) plus an optional application name. Example :

    DROPBOX_TOKEN="MYWONDERFULLTOKEN_HERE_FROM_DROPBOX_APP_INFO"
    DROPBOX_APPLICATION="MyWrapper"


example of backup:

		MongoServerHostConfiguration hostConf = new MongoServerHostConfiguration();
		MongodumpService mService = MongodumpService.getInstance(hostConf);
		BackupConfiguration backupConf = BackupConfiguration.getInstance("myDatabase", "myBackup");
		String backupLocalZipFile = mService.backup(backupConf);


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

### Maven

This project is hosted by Sonatype.org : [oss.sonatype.org](https://oss.sonatype.org/#nexus-search;quick~boly38)

 - [sonatype project ticket](https://issues.sonatype.org/browse/OSSRH-21400)
 - [sonatype requirements](http://central.sonatype.org/pages/requirements.html)
  
### Contributions

Contributions are welcome through feature branch and pull request. 

If you encounter issue, please provide details on a new [ticket](https://github.com/boly38/mongodbdump-java-wrapper/issues).

### TODO list

 - add the way to auto-detect mongo executables on an openshift context (at runtime on an openshift gear)
