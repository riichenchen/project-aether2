for i in range(10):
    fin = open("%i/info.txt"%i,"w")
    fin.write("1\n");
    fin.write("18,38\n");
    fin.write("0,100\n");
    fin.close()
