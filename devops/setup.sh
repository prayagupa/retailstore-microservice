curl -O https://download.java.net/java/GA/jdk12.0.1/69cfe15208a647278a19ef0990eea691/12/GPL/openjdk-12.0.1_linux-x64_bin.tar.gz
tar xvf openjdk-12.0.1_linux-x64_bin.tar.gz

sudo mv jdk-12.0.1 /opt/
cat <<EOF | sudo tee /etc/profile.d/jdk12.sh
export JAVA_HOME=/opt/jdk-12.0.1
export PATH=\$PATH:\$JAVA_HOME/bin
EOF

source /etc/profile.d/jdk12.sh
