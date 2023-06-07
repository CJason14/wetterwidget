<template>
  <div>
    <b-card border-variant="success" class="benachrichtigung">
      <b-card-body title="Benachrichtigungen" class="title">
        <switch-button v-model="switch1" @click.native="changeStatus()" class="button">{{status}}</switch-button>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
import { publicSigningKey } from "../main.js";
import SwitchButton from "./../components/SwitchButton";
export default {
  data: () => ({
    status: "Aus",
    switch1: false
  }),
  components: {
    SwitchButton
  },

  created() {
    this.checkSubscription();
  },

  methods: {
    changeStatus() {
      if (this.status == "Aus") {
        this.status = "An";
        this.subscribe();
      } else if (this.status == "An") {
        this.status = "Aus";
        this.unsubscribe();
      }
    },

    async unsubscribe() {
      const registration = await navigator.serviceWorker.ready;
      const subscription = await registration.pushManager.getSubscription();
      if (subscription) {
        const successful = await subscription.unsubscribe();
        if (successful) {
          console.info("Unsubscription successful");
          var url =
            window.location.protocol +
            "//" +
            window.location.hostname +
            ":8081/pushMessage/unsubscribe";
          await fetch(url, {
            method: "POST",
            body: JSON.stringify({ endpoint: subscription.endpoint }),
            headers: {
              "content-type": "application/json"
            }
          });

          console.info("Unsubscription info sent to the server");
        } else {
          console.error("Unsubscription failed");
        }
      }
    },

    async subscribe() {
      const registration = await navigator.serviceWorker.ready;
      const subscription = await registration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: publicSigningKey
      });

      console.info(`Subscribed to Push Service: ${subscription.endpoint}`);
      var url =
        window.location.protocol +
        "//" +
        window.location.hostname +
        ":8081/pushMessage/subscribe";
      await fetch(url, {
        method: "POST",
        body: JSON.stringify(subscription),
        headers: {
          "content-type": "application/json"
        }
      });

      console.info("Subscription info sent to the server");
    },

    async checkSubscription() {
      const registration = await navigator.serviceWorker.ready;
      const subscription = await registration.pushManager.getSubscription();
      if (subscription) {
        var url =
          window.location.protocol +
          "//" +
          window.location.hostname +
          ":8081/pushMessage/isSubscribed";
        const response = await fetch(url, {
          method: "POST",
          body: JSON.stringify({ endpoint: subscription.endpoint }),
          headers: {
            "content-type": "application/json"
          }
        });
        const subscribed = await response.json();

        if (subscribed) {
          this.status = "An";
          this.switch1 = true;
        }

        return subscribed;
      }

      return false;
    }
  }
};
</script>
<style scoped lang="scss">
.benachrichtigung {
  max-width: 350px;
  max-height: 200px;
  box-shadow: 1px 1px 2px #42b983;
  //position: fixed;
  position: absolute;
  top: 16px;
  right: 50px;
  z-index: 10;
}
.title {
  color: #42b983;
}

.button {
  color: black;
}
</style>