curl -L -O https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.5%2B11/OpenJDK21U-jdk_x64_linux_hotspot_21.0.5_11.tar.gz
tar xvf OpenJDK21U-jdk_x64_linux_hotspot_21.0.5_11.tar.gz

sudo mv jdk-21.0.5+11 /opt/
cat <<EOF | sudo tee /etc/profile.d/jdk21.sh
export JAVA_HOME=/opt/jdk-21.0.5+11
export PATH=\$PATH:\$JAVA_HOME/bin
EOF

source /etc/profile.d/jdk21.sh
