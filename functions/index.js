"use strict";

const {onCall, HttpsError} = require("firebase-functions/v2/https");
const {defineSecret, defineString} = require("firebase-functions/params");

// 1) Секрет Stripe (з Secret Manager)
const STRIPE_SECRET_KEY = defineSecret("STRIPE_SECRET_KEY");

// 2) Не-секретні параметри можна зробити як параметри середовища (опційно)
const STRIPE_CURRENCY = defineString("STRIPE_CURRENCY", {
  default: "usd",
});

exports.createPaymentIntent = onCall(
    {
      cors: true,
      secrets: [STRIPE_SECRET_KEY],
    },
    async (request) => {

      const data = request.data || {};

      const amount = Number(data.amount);

      if (!Number.isFinite(amount) || amount <= 0) {
        throw new HttpsError("invalid-argument", "amount має бути > 0 (у центах).");
      }

      try {
        const stripeSecret = STRIPE_SECRET_KEY.value();
        const stripe = require("stripe")(stripeSecret);

        const paymentIntent = await stripe.paymentIntents.create({
          amount: Math.round(amount),
          currency: STRIPE_CURRENCY.value(),
          automatic_payment_methods: {enabled: true},
        });

        return {
          clientSecret: paymentIntent.client_secret,
        };
      } catch (err) {
        console.error("Error creating PaymentIntent:", err);
        throw new HttpsError("internal", "PaymentIntent creation failed.");
      }
    },
);
