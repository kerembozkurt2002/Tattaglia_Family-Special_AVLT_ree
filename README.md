# Tattaglia_Family-Special_AVLT_ree
1 Introduction
You are working for the Lounge Intelligence Services. The Service is closely monitoring the
Tattaglia Crime Family and needs your help. Your duty is to keep track of the organization’s
structure using the tips from the informants and provide a detailed analysis of this structure.
2 Organization
Tattaglia Family believes in the golden mean, so they came up with the golden mean score
(GMS) devised from attributes such as criminal record, risk-taking, and reputation. They
are organized in a way that members that are closer to the golden mean are higher up in
the hierarchy:
Note: All GMS values in I/O files must be represented with 3 figures after the floating point.
For example: 5.000, 0.450, 0.123
2.1 Basics
• There is always a current boss of the family who resides at the top of the hierarchy.
The boss has a rank of 0 which is the highest rank.
• Every member has non-zero a rank, which indicates their importance to the family.
Rank is determined by their distance to the boss. For example, if a member’s superior
is the direct inferior of the boss, that member has a rank of 2.
• Every family member except the boss has a direct higher ranking member that gives
orders to them.
• Every family member has at most two direct lower-ranking members to give orders.
• If there are two lower-ranking members under the command of a higher-ranking member, one must have a higher GMS than the commander’s GMS, and one must have a
lower GMS than the commander’s GMS.
• Every new member initially joins the family at the bottom of the hierarchy.
• There are no two members that have the same GMS.
2.2 Keeping the Balance
• Tattaglia Family also believes that lower levels of the hierarchy are better. So, the
family ensures that among the members that have no members to command, the highest
ranking and the lowest ranking should not have more than 1 rank difference.
• This 1 rank difference is ensured by a reorganization process. After a new member
is welcomed into the family, all of the new member’s commanding superiors, starting
with the lowest ranking one to the highest ranking one, initializes the reorganization
process. Similarly, after a member leaves the family, commanding superiors of the
replacing member (or the leaving member if there are no replacements) initialize the
reorganization process.
2.2.1 Reorganization Process
1. If the height of the substructure that is commanded by reorganizing member’s inferior
with the lower GMS and the height of the substructure commanded by the inferior’s
inferior with the lower GMS is larger than the height of the substructure commanded
by the inferior’s inferior with the higher GMS, the inferior takes the place of the
reorganizing member.
2. If the height of the substructure that is commanded by reorganizing member’s inferior
with the higher GMS and the height of the substructure commanded by the inferior’s
inferior with the higher GMS is larger than the height of the substructure commanded
by the inferior’s inferior with the lower GMS, the inferior takes the place of the reorganizing member.
3. If the height of the substructure that is commanded by reorganizing member’s inferior
with the lower GMS and the height of the substructure commanded by the inferior’s
inferior with the higher GMS is larger than the height of the substructure commanded
by the inferior’s inferior with the lower GMS, the inferior’s inferior takes the place of
the reorganizing member.
4. If the height of the substructure that is commanded by reorganizing member’s inferior
with the higher GMS and the height of the substructure commanded by the inferior’s
inferior with the lower GMS is larger than the height of the substructure commanded
by the inferior’s inferior with the higher GMS, the inferior’s inferior takes the place of
the reorganizing member.
2.3 Leaving the Family
• If a member with no authority over another member leaves the family, nothing happens.
• If a member with only one direct member under their command leaves, that member
takes their place.
• If a member has two direct members under their command leaves, a member under the
leaving member’s command replaces them. The chosen member has the lowest GMS
among the candidates that has more GMS than the leaving member.
3 I/O Files
The input file consists of tips from informants and analysis requests by the service. All of
the commands in the input file will require you to log something to the output file, either a
tip log or an analysis result, which are explained in detail in the 4 - Functionality part. The
first line of the input file will be the name and the GMS of the boss, for which you don’t
have to output anything.
Example:
CORLEONE_VITO 0.500
MEMBER_IN CORLEONE_SONNY 0.700
MEMBER_IN CLEMENZA_PETER 0.300
INTEL_TARGET CORLEONE_SONNY 0.700 CLEMENZA_PETER 0.300
MEMBER_OUT CORLEONE_SONNY 0.700
INTEL_DIVIDE
For more information, you can refer to the additional input-output files.
4 Functionality
4.1 Tips
4.1.1 New Member Joins
Whenever a new member joins the family, you will be informed by the informants:
Structure: MEMBER_IN <SURNAME_NAME> <GMS>
Example: MEMBER_IN COSTIGAN_WILLIAM 0.400
To join the family, every member must meet and gain approval from their commanding
superiors. You are responsible for keeping the logs of these meetings, so you should log every
meeting, starting from the boss to the direct superior of the new member:
Structure: <SUPERIOR_SURNAME_NAME> welcomed <NEW_MEMBER_SURNAME_NAME>
Example: COSTELLO_FRANK welcomed COSTIGAN_WILLIAM
4.1.2 A Member Leaves
Whenever an existing member leaves the family, you will be informed by the informants:
Structure: MEMBER_OUT <SURNAME_NAME> <GMS>
Example: MEMBER_OUT COSTIGAN_WILLIAM 0.400
If a member left and was replaced by nobody, you should log:
Structure: <SURNAME_NAME> left the family, replaced by nobody
Example: COSTELLO_FRANK left the family, replaced by nobody
If a member left and was replaced by another member, you should log:
Structure: <LEAVING_MEMBER_SURNAME_NAME> left the family, replaced by
<REPLACING_MEMBER_SURNAME_NAME>
Example: COSTELLO_FRANK left the family, replaced by COSTIGAN_WILLIAM
4.2 Analysis
The Service will request an analysis of the state of the family before certain operations:
4.2.1 Targeting the Family
If The Service is planning simultaneous arrests of two family members, they will need to
sabotage their communication with the boss. To do this, they will want from you the lowest
ranking member that is the superior of both of these members.
Note: The Service will always make a request with two members.
Request:
Structure: INTEL_TARGET <SURNAME_NAME_1> <GMS_1> <SURNAME_NAME_2>
<GMS_2>
Example: INTEL_TARGET CORLEONE_SONNY 0.700 CLEMENZA_PETER 0.300
Analysis:
Structure: Target Analysis Result: <SURNAME_NAME> <GMS>
Example: Target Analysis Result: CORLEONE_VITO 0.500
4.2.2 Dividing the Family
If an operation tries to divide the whole family, The Service will request the maximum number of independent members that can be targeted. No two members in the target list should
be the direct superior or inferior of the other.
Request:
Structure: INTEL_DIVIDE
Analysis:
Structure: Division Analysis Result: <TARGET_LIST_SIZE>
Example: Division Analysis Result: 2
4.2.3 Monitoring Ranks in the Family
The Service also wants to investigate the ranks in the family to understand the missions
assigned to them to predict future actions that the family will take. For this purpose, they
will provide you with a member they have been recently monitoring and will want you to
give them the members with the same rank, including the member they provided.
Note: You should output the members sorted with increasing GMS.
Request:
Structure: INTEL_RANK <SURNAME_NAME> <GMS>
Example: INTEL_RANK CORLEONE_SONNY 0.700
Analysis:
Structure: Rank Analysis Result: <SURNAME_NAME_1> <GMS_1> <SURNAME_NAME_2>
<GMS_2> ...
Example: Rank Analysis Result: CLEMENZA_PETER 0.300 CORLEONE_SONNY
0.700

