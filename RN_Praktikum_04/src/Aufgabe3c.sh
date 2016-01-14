sudo /sbin/rcSuSEfirewall2 restart 
iptables --policy INPUT ACCEPT
iptables --policy OUTPUT ACCEPT
iptables --policy FORWARD ACCEPT

iptables –I INPUT –p tcp –s 172.16.1.0/24 –m tcp –m state –state NEW –j DROP
iptables –I INPUT –p tcp  –s 172.16.1.0/24 –m tcp –m state –state ESTABLISHED,RELATED –j DROP 
