sudo /sbin/rcSuSEfirewall2 restart 
iptables --policy INPUT DROP
iptables --policy OUTPUT DROP
iptables --policy FORWARD DROP
