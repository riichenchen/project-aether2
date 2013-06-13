fout = open("mymap.txt","w")

for  i in range(26,1600,50):
    for j in range(56,2000,56):
        fout.write("dirtblock %i %i %i\n"%(i,0,j))

fout.close()
