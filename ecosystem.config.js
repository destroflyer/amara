module.exports = {
  apps: [
    {
      name: 'amara',
      script: '/usr/lib/jvm/java-17-openjdk-amd64/bin/java',
      args: '-jar amara.jar',
      exp_backoff_restart_delay: 100,
    },
  ],
};
