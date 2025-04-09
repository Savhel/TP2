import 'package:mailer/mailer.dart';
import 'package:mailer/smtp_server.dart';

class EmailSender {
  final String subject;
  final String id;
  final String recipientEmail;

  EmailSender({
    required this.subject,
    required this.id,
    required this.recipientEmail,
  });

  Future<void> sendEmail(String message) async {
    // 1. Configuration SMTP (ex: Gmail)
    final smtpServer = gmail(
      'blife1255@gmail.com',
      'ljou etix vnnm zejo', // Utilisez un "Mot de passe d'application"
    );

    // 2. Création de l'email
    final email = Message()
      ..from = const Address('blife1255@gmail.com', 'Materiel Tracer Manager')
      ..recipients.add(recipientEmail)
      ..subject = '$subject - Phone : $id'
      ..text = message;

    // 3. Envoi de l'email
    try {
      await send(email, smtpServer);
      print('Email envoyé avec succès !');
    } on MailerException catch (e) {
      print('Erreur d\'envoi : ${e.message}');
    }
  }
}