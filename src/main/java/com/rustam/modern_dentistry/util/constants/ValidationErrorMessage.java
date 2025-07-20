package com.rustam.modern_dentistry.util.constants;

public interface ValidationErrorMessage {
    // Common messages - users
    String VALIDATION_USERNAME = "İstifadəçi adı daxil edin.";
    String VALIDATION_PASSWORD = "İstifadəçi şifrəsi daxil edin.";
    String VALIDATION_DATE = "Tarix daxil edin";
    String VALIDATION_NAME = "Ad daxil edin";
    String VALIDATION_SURNAME = "Soyad daxil edin";
    String VALIDATION_MOBILE = "Telefon nömrəsi daxil edin.";
    String VALIDATION_GENDER = "Cinsiyyəti seçin.";
    String VALIDATION_FIN_CODE = "Fin Kod daxil edin.";
    String VALIDATION_PATIENT_ID = "Pasient boş ola bilməz.";
    String VALIDATION_TECHNICIAN_ID = "Texnik boş ola bilməz.";
    String VALIDATION_DOCTOR_ID = "Həkim boş ola bilməz.";
    String VALIDATION_ROLE = "Istifadəçi rolunu seçin.";

    // Custom messages
    String VALIDATION_RECIPE_REQUIRED = "Resept seçin";
    String VALIDATION_BLACKLIST_ID_REQUIRED = "Qara siyahı səbəbini seçin.";
    String VALIDATION_LAB_PAYMENT_PRICE = "Məbləğ daxil edin.";
    String VALIDATION_LAB_DENTAL_WORK_STATUS = "Statusu daxil edin.";
    String VALIDATION_LAB_DENTAL_WORK_TYPE = "İş növünü daxil edin.";

}
