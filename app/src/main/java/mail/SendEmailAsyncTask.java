package mail;


import android.os.AsyncTask;
import android.support.compat.BuildConfig;
import android.util.Log;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;



/*
This AsyncTask sends an e-mail with 3 attachements.
No user interaction is required.
 */

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    // params, progress, result
    // doInBackground, onProgress, doPostExecute
    Mail m = new Mail("cognitiveassessments@gmail.com", "B3$U#Rufek7C");
    /*
    String _attachment1;
    String _attachment2;
    String _attachment3;
    */
    public SendEmailAsyncTask(String email_subject, String email_contents) {
        if (BuildConfig.DEBUG) Log.v(mail.SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");
        String[] toArr = {"cognitiveassessments@gmail.com"};
        m.setTo(toArr);
        m.setFrom("cognitiveassessments@gmail.com");
        m.setSubject(email_subject);
        m.setBody(email_contents);

        try {
            Log.v("EmailAsyncTask", "Mail sent");
        } catch(Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("EmailAsyncTask", "Could not send email", e);
        }
    }



    @Override
    protected Boolean doInBackground(Void... params) {
        if (BuildConfig.DEBUG) Log.v(mail.SendEmailAsyncTask.class.getName(), "doInBackground()");
        try {
            m.send();
            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(mail.SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            Log.e(mail.SendEmailAsyncTask.class.getName(), "Unable to send e-mail.");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    protected void onProgressUpdate(Void... progress) {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        /* File clean up - delete file after e-mail is sent
        Check if filepath exists, if so, delete it.
        */
    }


}