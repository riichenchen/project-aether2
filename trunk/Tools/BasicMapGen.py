fout = open("mymap.txt","w")

for  i in range(0,1600,50):
    for j in range(0,1200,28):
        fout.write("dirtblock %i %i %i\n"%(i,0,j))

fout.close()
