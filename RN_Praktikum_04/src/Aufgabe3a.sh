sudo /sbin/rcSuSEfirewall2 restart 
iptables –I  INPUT –s 172.16.1.0/24 –j DROP       
iptables –I OUTPUT –d 172.16.1.0/24 –j DROP   
iptables –I FORWARD –s 172.16.1.0/24 –j DROP 
iptables –I FORWARD –d 172.16.1.0/24 –j DROP 
