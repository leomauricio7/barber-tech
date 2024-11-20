package br.com.barbertech.utils;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PasswordUtil {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean validatePassword(String plainPassword, String hashedPassword) {
        // Gera o hash da senha informada pelo usuário
        String hashedInputPassword = PasswordUtil.hashPassword(plainPassword);

        // Compara o hash gerado com o hash salvo no banco
        return hashedInputPassword.equals(hashedPassword);
    }


    public static boolean isBeforeToday(Date selectedDate) {
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);

        Calendar todayCalendar = Calendar.getInstance();

        // Zerar horas, minutos, segundos e milissegundos para comparar apenas a data
        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        // Verifica se a data selecionada é anterior à data atual
        return selectedCalendar.before(todayCalendar);
    }

    public static boolean isSameDay(Date selectedDate) {
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);

        Calendar todayCalendar = Calendar.getInstance();

        // Zerar horas, minutos, segundos e milissegundos para comparar apenas a data
        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        // Comparar as datas (sem considerar a hora)
        return selectedCalendar.getTime().equals(todayCalendar.getTime());
    }

    public static String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(new Date());
    }


    public static int compareHours(String time1, String time2) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date1 = timeFormat.parse(time1);
        Date date2 = timeFormat.parse(time2);

        // Comparar as duas datas representando as horas
        return date1.compareTo(date2);
    }

}
