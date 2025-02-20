# Medication Reminder App

## Proje Açıklaması

Bu proje, Android cihazlar için geliştirilmiş bir ilaç hatırlatma uygulamasıdır. Kullanıcılar belirli bir tarih ve saat seçerek hatırlatıcı oluşturabilir ve cihazlarında bildirim alabilirler.

## Özellikler

- Kullanıcı belirli bir tarih ve saat seçerek hatırlatıcı oluşturabilir.
- Belirlenen zamanda sistem bildirimi gönderilir.
- Hatırlatıcı iptal edilebilir.
- Android 13 (Tiramisu) ve üzeri için bildirim izni isteği bulunur.

## Kullanılan Teknolojiler

- Kotlin
- Jetpack Compose (UI için)
- BroadcastReceiver (Alarm olaylarını dinlemek için)
- AlarmManager (Alarm zamanlaması için)
- PendingIntent (Broadcast gönderimi için)
- NotificationManager (Bildirim oluşturmak için)

### Gerekli İzinleri Verin:

Android 13 (API 33) ve üzeri için `POST_NOTIFICATIONS` iznini vermeniz gerekmektedir.

## Kullanım

1. Tarih ve saat seçin
2. Hatırlatma mesajı girin
3. **Schedule** butonuna basarak alarmı ayarlayın
4. **Cancel** butonuyla alarmı iptal edin

## Gerekli İzinler

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

## Gelecek Geliştirmeler

- Kullanıcının ayarladığı hatırlatıcıları listeleme
- Tekrar eden hatırlatıcılar ekleme
- Room DB ile kayıtlı hatırlatıcıları saklama
