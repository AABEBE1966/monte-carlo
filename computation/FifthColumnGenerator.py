# %Five_Column_Canvases_Generator
# %Generates 15 canvas csv files in which the first four columns are the
# %canvas you generated with GradedGenerator2_Sandwich.m and the fifth column
# %of the nth file is the nth column of total_ex_gen_morphblend.
#
# %Do not use this script until total_ex_gen_morphblend is replaced with the
#     %correct source for fifth-column data; this script will take a long time to
# %run and, as the canvases are useless until we know where the fifth column
# %should come from, you'll have wasted a lot of time.

import matlab.engine

class FifthColumnGenerator:
    def __init__(self, structure_file):
        self.structure_file=structure_file
        self.opticalGenerationMicroScale()
    def opticalGenerationMicroScale(self):
        eng = matlab.start_matlab()
        total_ex_gen_morphblend=eng.OpticalGenerationMicroscaleAlt(self.structure_file,nargout=0)


fifth=FifthColumnGenerator("/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject/src/data/.structure_0_.csv")
# load('matlab','total_ex_gen_morphblend'); % loading total_ex_gen_morphblend from the workspace variables
# CanvasName = input('Enter canvas name: ', 's'); % YourStructure.csv
# Five_c_Canvas = csvread(CanvasName);
#
# for k = 1:15
# Five_c_Canvas(:,5) = total_ex_gen_morphblend(:,k);
# structure_name = strsplit(CanvasName, '.');
# newname = strcat(structure_name(1), string(k), '.csv');
# csvwrite(char(newname), Five_c_Canvas) % The nth super canvas will be named YourStructuren.csv
# end
