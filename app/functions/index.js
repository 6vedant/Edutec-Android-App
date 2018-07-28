
var functions = require('firebase-functions');
var admin = require('firebase-admin');
 var nodemailer = require('nodemailer');


admin.initializeApp(functions.config().firebase);
 
 
var topic = "all";

 var db = admin.database();
 var ref = db.ref();
 

 exports.sendNotification = functions.database.ref('/watch_online/packages/pa/videos/{articleId}')
        .onCreate(event => {
 
        // Grab the current value of what was written to the Realtime Database.
        var eventSnapshot = event.data;
        var str1 = "Author is ";
        var str = eventSnapshot.child("thumb_url").val();

        console.log(str);
 
        console.log(topic); 
        var payload = {
            data: {
                title: eventSnapshot.child("video_title").val(),
                image_url: str,
                body: eventSnapshot.child("video_url").val()

            }
        };
 
        // Send a message to devices subscribed to the provided topic.
        return admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                // See the MessagingTopicResponse reference documentation for the
                // contents of response.
                console.log("Successfully sent message:", response);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
        });




 var mailtransport = nodemailer.createTransport({
   host: 'smtp.gmail.com',
   port: 465,
   service: 'gmail',
   secure: true,
   auth: {
     user: 'contactedutec@gmail.com',
     pass: 'edutec17'
   }
 });

  const APP_NAME = 'Edutec';

  exports.sendWelcomeEmail = functions.auth.user().onCreate(event => {
// [END onCreateTrigger]
  // [START eventAttributes]
  const user = event.data; // The Firebase user.

  const email = user.email; // The email of the user.
  const displayName = user.displayName; // The display name of the user.
  // [END eventAttributes]

  return sendWelcomeEmail(email, displayName);
});


  // Sends a welcome email to the given user.
function sendWelcomeEmail(email, displayName) {
  const mailOptions = {
    from: `${APP_NAME} <noreply@firebase.com>`,
    to: email
  };

  // The user subscribed to the newsletter.
  mailOptions.subject = `Welcome to ${APP_NAME}!`;
  mailOptions.text = `Hey ${displayName || ''}! Welcome to ${APP_NAME}. I hope you will enjoy our service.`;
  return mailtransport.sendMail(mailOptions).then(() => {
    console.log('New welcome email sent to:', email);
  });
}



