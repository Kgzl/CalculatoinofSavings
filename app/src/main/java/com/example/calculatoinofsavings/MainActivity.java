package com.example.calculatoinofsavings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        public final int creditAmount = 14_000;
        public final int salary = 2_500;
        public float account = 1_000;
        public final int bankPercent = 5;
        public final int spendablePercent = 100;
        public float[] monthlyPayments = new float[60];

        public void calcCredit() {
            float creditBalance = creditAmount + calcPercentOf(creditAmount, bankPercent) - account;
            account = 0;

            for (int i = 0 ; creditBalance > 0 && i < 60; i++) {
                account += calcPercentOf(salary, spendablePercent);
                if (i % 12 == 0 && i != 0) creditBalance += calcPercentOf(creditBalance, bankPercent);
                monthlyPayments[i] = Math.min(creditBalance, account);
                creditBalance -= account;
                account = 0;
                System.out.println(creditBalance);
            }
        }

        public float calcPercentOf(float amount, float percent) {
            return amount * percent / 100;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            TextView creditTermOut = findViewById(R.id.creditTermOut);
            TextView monthlyPaymentsOut = findViewById(R.id.monthlyPaymentsOut);

            StringBuilder monthlyPaymentsOutText = new StringBuilder();
            int monthCounter = 0;

            monthlyPaymentsOutText.append("Первоначальный взнос: ").append(account).append('\n');
            monthlyPaymentsOutText.append("Выплаты: ");

            calcCredit();
            for (float payment : monthlyPayments) {
                if (payment <= 0) break;
                monthlyPaymentsOutText.append(payment).append(" монет; ");
                monthCounter++;
            }

            creditTermOut.setText("Можно будет накопить за " + monthCounter + " месяцев");
            monthlyPaymentsOut.setText(monthlyPaymentsOutText.toString());
        }
    }

