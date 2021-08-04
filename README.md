# Signet Repair Materials Management Microservice

This is the Repair Materials Management Queue Processor Microservice:
 * supply orders
 * supply order line items
 * receipt
 * receipt line items
 * users
 * vendors

### YAML Lint (life saver!)
Navigate to the link below to find errors in YAML files.  GREAT for finding insidious bugs in the `buildspec.yml`!

https://jsonformatter.org/yaml-formatter

### DockerHub Pull Restrictions
To deal with the DockerHub pull restrictions read this blog post:

https://medium.com/rockedscience/fixing-docker-hub-rate-limiting-errors-in-ci-cd-pipelines-ea3c80017acb

You will need to add the following to the AIM Role created with CodePipeline:

`
{
    "Effect": "Allow",
    "Action": [
        "ssm:GetParameter",
        "ssm:GetParameters"
    ],
    "Resource": "arn:aws:ssm:{AWS_REGION}:{YOUR_AWS_ACCOUNT_ID}:parameter/my-application/dockerhub/*"
},
{
    "Effect": "Allow",
    "Action": [
        "kms:Decrypt",
        "kms:GenerateDataKey*"
    ],
    "Resource": [
        "{YOUR_KMS_KEY_ID_HERE}"
    ]
},
`

### Maven Helpers
`mvn -Dmaven.test.skip package`

## Push Docker image to ECR
* aws ecr create-repository --repository-name rmm-queue-service --image-scanning-configuration scanOnPush=true --region us-east-2

* aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 415353467794.dkr.ecr.us-east-2.amazonaws.com
* docker tag rmm-queue-service:latest 415353467794.dkr.ecr.us-east-2.amazonaws.com/rmm-queue-service:latest
* docker push 415353467794.dkr.ecr.us-east-2.amazonaws.com/rmm-queue-service:latest

## Delete an ECR image
* aws ecr batch-delete-image --repository-name om-repository --image-ids imageTag=latest
